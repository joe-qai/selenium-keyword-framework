# Selenium Keyword-Driven Automation Framework

> 基于 Selenium 的关键字驱动 UI 自动化测试框架，结合 PageFactory 模式实现高效、可维护的自动化测试。

## 📋 项目概述

本框架采用**关键字驱动**与**PageFactory**两种设计模式相结合的方式，实现了一套灵活、可扩展的 UI 自动化测试解决方案。

### 核心特性

| 特性 | 描述 |
|------|------|
| **关键字驱动** | 通过 Excel 配置测试用例，无需编码即可扩展测试场景 |
| **PageFactory模式** | 基于注解的页面元素管理，提高代码复用性 |
| **反射机制** | 动态调用关键字方法，实现灵活的测试执行 |
| **ExtentReports** | 丰富的测试报告生成，支持 HTML 格式 |
| **自动截图** | 测试失败时自动捕获屏幕截图 |
| **日志记录** | 基于 Log4j 的详细日志记录 |

## 🏗️ 技术栈

- **语言**: Java 8+
- **测试框架**: TestNG
- **自动化工具**: Selenium WebDriver 3.141.59
- **报告工具**: ExtentReports 3.0.6
- **Excel处理**: Apache POI 3.17
- **日志框架**: Log4j 1.2.17

## 📁 项目结构

```
selenium_keyword_pageFactory/
├── src/
│   ├── main/java/
│   │   ├── selenium/
│   │   │   ├── keyword/
│   │   │   │   ├── Kwds/           # 关键字驱动实现
│   │   │   │   │   └── KeywordsDriven.java
│   │   │   │   ├── common/         # 通用引擎
│   │   │   │   │   ├── Common_Engine.java
│   │   │   │   │   ├── Constants.java
│   │   │   │   │   └── Screenshots.java
│   │   │   │   ├── extenreports/   # 报告扩展
│   │   │   │   ├── starter/        # 启动引擎
│   │   │   │   │   └── StartEngine.java
│   │   │   │   └── utility/        # 工具类
│   │   │   └── pom/
│   │   │       └── pageObjects/    # 页面对象
│   ├── main/resources/             # 资源文件
│   ├── test/java/                  # 测试用例
│   └── test/resources/             # 测试数据
├── drivers/                        # WebDriver 驱动
├── testSuits/                      # TestNG 测试套件配置
├── Log/                            # 日志输出目录
└── pom.xml                         # Maven 配置
```

## 🚀 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.6+
- Chrome Browser (推荐)

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/joe-qai/selenium-keyword-framework.git
   cd selenium_keyword_pageFactory
   ```

2. **构建项目**
   ```bash
   mvn clean compile
   ```

3. **运行测试**
   ```bash
   mvn test
   ```

## 📊 使用方法

### 1. 配置测试数据

编辑 `src/test/resources/dataEngine.xlsx` 文件：

| 步骤 | 关键字 | 元素定位 | 值 |
|------|--------|----------|----|
| 1 | OpenBrowser | - | - |
| 2 | Navigate | - | https://example.com |
| 3 | Input | id;username | testuser |
| 4 | Input | id;password | password123 |
| 5 | Click | id;loginBtn | - |
| 6 | CloseBrowser | - | - |

### 2. 关键字语法

元素定位采用 `定位方式;元素值` 的格式：

```
id;elementId              // 通过 ID 定位
xpath;//div[@class='test'] // 通过 XPath 定位
css;.className            // 通过 CSS 选择器定位
name;elementName          // 通过 name 属性定位
linktext;LinkText         // 通过链接文本定位
partiallinktext;Partial   // 通过部分链接文本定位
tagname;div               // 通过标签名定位
classname;className       // 通过类名定位
```

### 3. 支持的关键字

| 关键字 | 描述 | 参数 |
|--------|------|------|
| `OpenBrowser` | 打开浏览器 | - |
| `Navigate` | 导航到URL | URL地址 |
| `Click` | 点击元素 | 元素定位 |
| `Input` | 输入文本 | 元素定位, 输入值 |
| `CloseBrowser` | 关闭浏览器 | - |
| `WaitMoment` | 等待指定时间 | 毫秒数 |
| `JScriptExcuteClick` | JavaScript 执行点击 | 元素定位 |

### 4. 运行测试套件

```bash
# 通过 TestNG XML 运行
mvn test -DsuiteXmlFile=testSuits/test.xml

# 运行特定测试类
mvn test -Dtest=testIndex
```

## 🔧 框架架构

```
┌─────────────────────────────────────────────────────────────────┐
│                      测试执行流程                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│  │  Excel数据源  │───>│  StartEngine │───>│  Common_Engine   │  │
│  │  (Test Data) │    │  (启动引擎)   │    │  (反射执行引擎)   │  │
│  └──────────────┘    └──────────────┘    └────────┬─────────┘  │
│                                                   │             │
│                                                   ▼             │
│                                        ┌──────────────────┐    │
│                                        │  KeywordsDriven  │    │
│                                        │  (关键字实现层)   │    │
│                                        └────────┬─────────┘    │
│                                                   │             │
│                                                   ▼             │
│                                        ┌──────────────────┐    │
│                                        │   Selenium API   │    │
│                                        │   (浏览器驱动)    │    │
│                                        └──────────────────┘    │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  报告输出                                                 │   │
│  │  ├─ ExtentReports (HTML)                                 │   │
│  │  ├─ Log4j (日志文件)                                      │   │
│  │  └─ Excel (结果回写)                                      │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## 📝 PageFactory 模式示例

```java
public class LoginPage {
    
    WebDriver driver;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(id = "username")
    @CacheLookup
    WebElement usernameInput;
    
    @FindBy(id = "password")
    WebElement passwordInput;
    
    @FindBy(id = "login-btn")
    WebElement loginButton;
    
    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        loginButton.click();
    }
}
```

## 📈 报告输出

测试完成后，报告将生成在以下位置：
- **HTML 报告**: `target/extent-report.html`
- **日志文件**: `Log/serverDebug.log`
- **结果 Excel**: `src/test/resources/dataEngine.xlsx` (已更新)

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/your-feature`)
3. 提交更改 (`git commit -am 'Add some feature'`)
4. 推送到分支 (`git push origin feature/your-feature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 详见 `LICENSE` 文件

## 📧 联系方式

如有问题或建议，请通过以下方式联系：
- 邮箱: joe-tester@example.com
- GitHub: [@Joe-Tester](https://github.com/Joe-Tester)

---

*Powered by Selenium WebDriver & TestNG*

---

## 更新日志

### v0.0.1 (2021-09-08)
- 初始版本发布
- 支持关键字驱动测试
- 集成 PageFactory 模式
- 支持 ExtentReports 报告生成