# USE CASE: 3 Produce a Report on the Salary of Employees of My Department

## CHARACTERISTIC INFORMATION

### Goal in Context

As a *department manager* I want to *produce a report on the salary of employees in my department* so that *I can 
support financial reporting for my department.*

### Scope

Department.

### Level

Primary task.

### Preconditions

We know our department. Database contains current employee salary data.

### Success End Condition

A report is available for the department manager.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager.

### Trigger

The department manager initiates request.

## MAIN SUCCESS SCENARIO

1. Department manager extracts current salary information of all employees in the given department.
2. Report is exported for review.

## EXTENSIONS

1. **DEPARTMENT DOESN'T EXIST**
   1. Department Manager enters correct department.

## SUB-VARIATIONS

None.