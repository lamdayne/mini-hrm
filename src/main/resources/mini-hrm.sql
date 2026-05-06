-- ENUM TYPES

CREATE TYPE user_role AS ENUM (
    'SYSTEM_ADMIN',
    'COMPANY_ADMIN',
    'EMPLOYEE'
    );

CREATE TYPE tenant_status AS ENUM (
    'ACTIVE',
    'LOCKED',
    'PENDING'
    );

CREATE TYPE employee_status AS ENUM (
    'ACTIVE',
    'PROBATION',
    'RESIGNED'
    );

CREATE TYPE gender AS ENUM (
    'MALE',
    'FEMALE',
    'OTHER'
    );

CREATE TYPE attendance_status AS ENUM (
    'CHECKED_IN',
    'CHECKED_OUT'
    );

-- TABLE: tenants

CREATE TABLE tenants
(
    id                     BIGSERIAL PRIMARY KEY,
    name                   VARCHAR(255)  NOT NULL,
    tax_code               VARCHAR(20)   NOT NULL,
    representative_email   VARCHAR(255)  NOT NULL,
    phone                  VARCHAR(20)   NOT NULL,
    status                 tenant_status NOT NULL DEFAULT 'PENDING',
    standard_hours_per_day NUMERIC(4, 2) NOT NULL DEFAULT 8,
    created_at             TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    deleted_at             TIMESTAMPTZ   NULL
);

CREATE UNIQUE INDEX uq_tenants_tax_code ON tenants (tax_code);
CREATE UNIQUE INDEX uq_tenants_rep_email ON tenants (representative_email);
CREATE INDEX idx_tenants_status ON tenants (status);

-- TABLE: users

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    tenant_id     BIGINT       NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          user_role    NOT NULL,
    is_active     BOOLEAN      NOT NULL DEFAULT TRUE,
    employee_id   BIGINT       NULL,
    last_login_at TIMESTAMPTZ  NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    deleted_at    TIMESTAMPTZ  NULL,
    CONSTRAINT fk_users_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX uq_users_email ON users (email);
CREATE INDEX idx_users_tenant_id ON users (tenant_id);
CREATE INDEX idx_users_tenant_role ON users (tenant_id, role);
CREATE UNIQUE INDEX uq_users_employee_id ON users (employee_id);

-- TABLE: departments

CREATE TABLE departments
(
    id          BIGSERIAL PRIMARY KEY,
    tenant_id   BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT         NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    deleted_at  TIMESTAMPTZ  NULL,
    CONSTRAINT fk_departments_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE RESTRICT
);

CREATE INDEX idx_departments_tenant_id ON departments (tenant_id);

CREATE UNIQUE INDEX uq_departments_tenant_name
    ON departments (tenant_id, name)
    WHERE deleted_at IS NULL;

-- TABLE: positions

CREATE TABLE positions
(
    id          BIGSERIAL PRIMARY KEY,
    tenant_id   BIGINT       NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT         NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    deleted_at  TIMESTAMPTZ  NULL,

    CONSTRAINT fk_positions_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE RESTRICT
);

CREATE INDEX idx_positions_tenant_id ON positions (tenant_id);

-- Partial unique index
CREATE UNIQUE INDEX uq_positions_tenant_name
    ON positions (tenant_id, name)
    WHERE deleted_at IS NULL;

-- TABLE: employees

CREATE TABLE employees
(
    id            BIGSERIAL PRIMARY KEY,
    tenant_id     BIGINT          NOT NULL,
    employee_code VARCHAR(50)     NOT NULL,
    full_name     VARCHAR(255)    NOT NULL,
    date_of_birth DATE            NULL,
    gender        gender          NULL,
    email         VARCHAR(255)    NULL,
    phone         VARCHAR(20)     NULL,
    address       TEXT            NULL,
    department_id BIGINT          NULL,
    position_id   BIGINT          NULL,
    start_date    DATE            NOT NULL,
    status        employee_status NOT NULL DEFAULT 'PROBATION',
    created_at    TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    deleted_at    TIMESTAMPTZ     NULL,

    CONSTRAINT fk_employees_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE RESTRICT,
    CONSTRAINT fk_employees_department FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE SET NULL,
    CONSTRAINT fk_employees_position FOREIGN KEY (position_id) REFERENCES positions (id) ON DELETE SET NULL
);

CREATE INDEX idx_employees_tenant_id ON employees (tenant_id);
CREATE UNIQUE INDEX uq_employees_tenant_code ON employees (tenant_id, employee_code);
CREATE INDEX idx_employees_tenant_name ON employees (tenant_id, full_name);
CREATE INDEX idx_employees_department_id ON employees (department_id);
CREATE INDEX idx_employees_status ON employees (status);

ALTER TABLE users
    ADD CONSTRAINT fk_users_employee
        FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE SET NULL;

-- TABLE: attendance_logs

CREATE TABLE attendance_logs
(
    id             BIGSERIAL PRIMARY KEY,
    tenant_id      BIGINT            NOT NULL,
    employee_id    BIGINT            NOT NULL,
    work_date      DATE              NOT NULL,
    check_in_time  TIMESTAMPTZ       NOT NULL,
    check_out_time TIMESTAMPTZ       NULL,
    worked_hours   NUMERIC(5, 2)     NOT NULL DEFAULT 0,
    status         attendance_status NOT NULL DEFAULT 'CHECKED_IN',
    created_at     TIMESTAMPTZ       NOT NULL DEFAULT NOW(),
    updated_at     TIMESTAMPTZ       NOT NULL DEFAULT NOW(),
    deleted_at     TIMESTAMPTZ       NULL,
    CONSTRAINT fk_attendance_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE RESTRICT,
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE RESTRICT
);

CREATE INDEX idx_attendance_tenant_id ON attendance_logs (tenant_id);
CREATE INDEX idx_attendance_tenant_date ON attendance_logs (tenant_id, work_date);
CREATE INDEX idx_attendance_employee_id ON attendance_logs (employee_id);
CREATE INDEX idx_attendance_work_date ON attendance_logs (work_date);

-- Partial unique index
CREATE UNIQUE INDEX uq_attendance_employee_date ON attendance_logs (employee_id, work_date) WHERE deleted_at IS NULL;

-- TABLE: employee_status_history

CREATE TABLE employee_status_history
(
    id          BIGSERIAL PRIMARY KEY,
    tenant_id   BIGINT          NOT NULL,
    employee_id BIGINT          NOT NULL,
    old_status  employee_status NULL,
    new_status  employee_status NOT NULL,
    changed_by  BIGINT          NOT NULL,
    note        TEXT            NULL,
    changed_at  TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_employee_status_history_tenant FOREIGN KEY (tenant_id) REFERENCES tenants (id) ON DELETE RESTRICT,
    CONSTRAINT fk_employee_status_history_employee FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE RESTRICT,
    CONSTRAINT fk_employee_status_history_changed_by FOREIGN KEY (changed_by) REFERENCES users (id) ON DELETE RESTRICT
);

CREATE INDEX idx_emp_status_hist_employee ON employee_status_history (employee_id);
CREATE INDEX idx_emp_status_hist_tenant ON employee_status_history (tenant_id);