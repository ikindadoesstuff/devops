# USE CASE: 8 Update Employee's Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want to *delete an employee's details* so that *the company is compliant with data retention 
legislation*.

### Scope

Employee Management System.

### Level

Primary task.

### Preconditions

We know the employee's name. Employee exists in the database.

### Success End Condition

Employee details are successfully deleted.

### Failed End Condition

Employee details in database remain.

### Primary Actor

HR Advisor.

### Trigger

An employee has left the company.

## MAIN SUCCESS SCENARIO

1. HR advisor selects option to delete employee information.
2. HR advisor selects employee by name.
3. HR advisor confirms deletion.

## EXTENSIONS

2. **Entered Name Does Not Exist**:
   1. HR advisor enters correct employee name.
   2. HR advisor confirms employee deletion.
2. **HR Advisor Selects Wrong Employee**:
    1. HR advisor enters correct employee name.
    2. HR advisor confirms employee deletion.

## SUB-VARIATIONS

None.
