# Agents Configuration

## Overview
This document describes the agent configuration for the Selenium Keyword-Driven Automation Framework.

## Agent Types

### 1. Execution Agent
Responsible for running test cases and executing keyword actions.

#### Configuration Parameters:
| Parameter | Description | Default Value |
|-----------|-------------|---------------|
| `browserType` | Browser type for execution | `chrome` |
| `implicitWait` | Implicit wait timeout (seconds) | `20` |
| `pageLoadTimeout` | Page load timeout (seconds) | `30` |
| `scriptTimeout` | Script execution timeout (seconds) | `30` |

### 2. Reporting Agent
Handles test result reporting and documentation.

#### Supported Reports:
- ExtentReports (HTML)
- Log4j Logging
- Excel Result Tracking

### 3. Data Agent
Manages test data from external sources.

#### Data Sources:
- Excel Files (.xls, .xlsx)
- Property Files (.properties)

## Agent Configuration Example

```properties
# Browser Configuration
browser.type=chrome
driver.path=drivers/chromedriver.exe

# Timeout Configuration
timeout.implicit=20
timeout.pageLoad=30
timeout.script=30

# Report Configuration
report.enabled=true
report.path=reports/
report.extent.enabled=true

# Log Configuration
log.level=INFO
log.path=Log/
```

## Agent Execution Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    Test Execution Flow                      │
├─────────────────────────────────────────────────────────────┤
│  1. StartEngine Agent                                      │
│     └── Reads Test Suite from Excel                         │
│                                                            │
│  2. Common_Engine Agent                                    │
│     └── Executes Keywords via Reflection                    │
│                                                            │
│  3. KeywordsDriven Agent                                   │
│     └── Performs Selenium Actions                          │
│                                                            │
│  4. Reporting Agent                                        │
│     └── Generates Test Reports                             │
└─────────────────────────────────────────────────────────────┘
```

## Agent Components

| Component | Package | Responsibility |
|-----------|---------|----------------|
| StartEngine | `selenium.keyword.starter` | Test suite orchestration |
| Common_Engine | `selenium.keyword.common` | Keyword execution via reflection |
| KeywordsDriven | `selenium.keyword.Kwds` | Selenium action implementation |
| ExcelUtils | `selenium.keyword.utility` | Excel data handling |
| ExtentReports | `selenium.keyword.extenreports` | Test report generation |

## Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `DRIVER_PATH` | Path to WebDriver executables | `drivers/chromedriver.exe` |
| `EXCEL_FILE` | Path to test data Excel | `src/test/resources/dataEngine.xlsx` |
| `LOG_PATH` | Path to log files | `Log/` |

## Agent Initialization

```java
// Agent initialization sequence
1. Load Configuration (env.properties)
2. Initialize WebDriver
3. Load Excel Test Data
4. Execute Test Suite
5. Generate Reports
6. Cleanup Resources
```

## Error Handling

Agents implement robust error handling:
- **Screenshot Capture**: On test failure, automatically captures screenshot
- **Result Tracking**: Updates Excel with pass/fail status
- **Log Recording**: Detailed logging of execution flow