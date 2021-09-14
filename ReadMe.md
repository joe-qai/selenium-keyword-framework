### 采用Selenium工具设计UI自动化测试框架
```
众所周知：在UI自动化测试框架方面，有多种设计模式；而其中PO模式为线上测试培训机构所推崇；
第一它真的非常实用，尤其对于自身编码能力的提升及思想扩展，乃至于PageFactory模式用起来得心应手；
第二它真的非常简单，培训不需要对它有过多的解释，只要有代码基础，能理解对象和封装，基本就可以跑起来；
第三说是企业级，实则是小创业型团队，所以建议是能用、会用；即能通过面试，基本就可以抛弃UI自动化，拥抱接口自动化。
```
- 既然说到UI自动化测试框架的设计方式，那么来浅谈一下：
#### PO模式
```
这里就不过多解释，通俗点讲：就是将测试脚本和页面元素及操作方法分离；
po思想：结构分层，测试脚本即业务逻辑层，就是测试用例，调用对象库(Page Object)，测试业务流程；
页面元素和操作方法就是页面对象，即对象逻辑层；这里有必要再分一下：对象层即元素对应一个操作方法；而逻辑层即操作方法的集合<组装操作方法成一个流程>；
有些说法还有一层，有些用例需要用到多组数据，随即将数据抽出，变成数据层。
```
- po优势：效率高、复用性高；
```
怎样理解优势：也就是在业务逻辑不变的情况下，只需要关注对象库即元素是否发生改变，否则只需要继续堆砌测试用例即可；
代码复用性高，这更好理解，无非就是在不同测试用例的情况下，使用到了同一个操作步骤，那么就达到了提升代码复用性的目的。
```
- PageFactory模式是po模式的补充，其中使用到更多的是注解；代码中没有findElement等方法，全靠PageFactory.initElements()初始化；然后动态代理和反射调用。
```java
/**
 * 
 * TODO:基于PageFactory模式设计对象库
 *
 * @author Joe-Tester
 * @time 2021年9月8日
 * @file SearchPageFactory.java
 */
public class SearchPageFactory {

	WebDriver driver;
	
	/**
	 * 构造方法
	 * @param driver
	 */
	public SearchPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "tab-flight-tab-hp")
	@CacheLookup
	WebElement fightTab;

	@FindBy(id = "flight-type-roundtrip-label")
	WebElement roundTrip;

	@FindBy(id = "flight-type-multi-dest-label")
	WebElement multipleDestination;

	@FindBy(xpath = "//*[@id='gcw-flights-form-hp-flight']/div[3]/div[1]/div/div[1]/label/input")
	WebElement origin;

	@FindBy(xpath = "//*[@id='flight-destination-hp-flight']")
	WebElement destination;

	@FindBy(id = "flight-departing-hp-flight")
	WebElement departure;

	@FindBy(id = "flight-returning-wrapper-hp-flight")
	WebElement returning;

	@FindBy(xpath = "//button/span[@class='icon icon-close']")
	WebElement closeIcon;

	@FindBy(xpath = "//*[@id='gcw-flights-form-hp-flight']/div[8]/label/button")
	WebElement searchButton;

	@FindBy(xpath = "//button[@class='datepicker-close-btn close btn-text']")
	WebElement closeCalIcon;

	/***
	 * Click Fight Tab
	 */
	public void clickFightTab() {
		fightTab.click();
	}

	/***
	 * Click Round Trip
	 */
	public void clickRoundTrip() {
		roundTrip.click();
	}

	/***
	 * Click Multiple Destination
	 */
	public void clickMultipleDestination() {
		multipleDestination.click();
	}

	/***
	 * Click Close Icon
	 */
	public void clickCloseIcon() {
		closeIcon.click();
	}

	/***
	 * Set Origin City
	 * 
	 * @param originCity
	 */
	public void setOriginCity(String originCity) {
		origin.sendKeys(originCity);
	}

	/***
	 * Set Destination City
	 * 
	 * @param destinationCity
	 */
	public void setDestinationCity(String destinationCity) {
		destination.sendKeys(destinationCity);
	}

	/***
	 * Set Departure Date
	 * 
	 * @param departureDate
	 */
	public void setDeaprtureDate(String departureDate) {
		departure.sendKeys(departureDate);
	}

	public void setReturnDate(String returnDate) {
		returning.sendKeys(returnDate);
	}

	/***
	 * Click Search Button
	 */
	public void clickSearchButton() {
		searchButton.click();
	}

	/***
	 * Click Calendar Menu close icon
	 */
	public void clickCalendar() {
		closeCalIcon.click();
	}

	/**
	 * closing the browser
	 */
	public void quit() {
		driver.quit();
	}
}
```
#### 关键字框架设计
```
这套框架里面，使用的关键技术就是反射，原理就是在程序运行中，从excel中读取关键字，然后通过反射去关键字类调用该方法。
在实际应用中，也有几层意思：
1、excel外部文件介质，存储关键字和页面元素；
2、程序设计PageObject实际文件存放页面元素；
3、程序内部实现对页面元素的操作方法供反射调用；
算是po与关键字框架的结合：参考![关键字框架设计源码](https://gitee.com/hellotester/SeleniumKeywordDrive/tree/master)
```
##### PO+关键字
```
这套组合需要维护两个地方，一是业务逻辑发生改变了就去维护excel、二是页面元素发生改变了再去修改页面元素；
```
##### 纯关键字框架
```
如此设计有一个目的：就是所有数据、页面元素、对象库都只维护一份excel、并且易于扩展集成web平台；
那么唯一出现的问题就是不方便调试，或者说缺少啥关键字就去开发某关键字即可。
```
