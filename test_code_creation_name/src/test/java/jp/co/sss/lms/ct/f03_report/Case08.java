package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//URLにアクセス
		goTo("http://localhost:8080/lms");

		//タイトルを確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインID入力
		WebElement id = webDriver.findElement(By.id("loginId"));
		id.clear();
		id.sendKeys("StudentAA02");

		// パスワード入力
		WebElement pass = webDriver.findElement(By.id("password"));
		pass.clear();
		pass.sendKeys("StudentAA01");

		// ログインボタン押下
		WebElement loginButton = webDriver.findElement(By.className("btn-primary"));
		loginButton.click();

		// コース詳細画面のタイトル確認
		WebDriverUtils.visibilityTimeout(By.cssSelector(".navbar-brand"), 10);
		assertEquals("コース詳細 | LMS", WebDriverUtils.webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 提出済みの研修日の行を取得
		WebElement submitted = webDriver.findElement(By.xpath("//span[text()='提出済み']/ancestor::tr"));

		// 提出済みの行にある「詳細」ボタンを取得
		WebElement detailButton = submitted.findElement(By.cssSelector("input[type='submit'][value='詳細']"));

		// 「詳細」ボタン押下
		detailButton.click();

		// セクション詳細画面の表示を待機
		WebDriverUtils.visibilityTimeout(By.id("section"), 10);

		// タイトル確認
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「確認する」ボタンを取得
		WebElement confirmButton = webDriver.findElement(By.xpath("//input[contains(@value, 'を確認する')]"));

		// 「確認する」ボタン押下
		confirmButton.click();

		// レポート登録画面の表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".navbar-brand"), 10);

		// レポート登録画面のタイトル確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 報告内容を取得
		WebElement report = webDriver.findElement(By.id("content_0"));

		// 報告内容を修正
		report.clear();
		report.sendKeys("報告内容を修正しました。");

		// 「提出する」ボタンを取得
		WebElement submitButton = webDriver.findElement(By.xpath("//button[text()='提出する']"));

		// 「提出する」ボタン押下
		submitButton.click();

		// セクション詳細画面の表示を待機
		WebDriverUtils.visibilityTimeout(By.id("section"), 10);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// 「ようこそ○○さん」リンクを取得
		WebElement userLink = webDriver.findElement(
				By.cssSelector("a[href='/lms/user/detail']"));

		// 「ようこそ○○さん」リンク押下
		userLink.click();

		// ユーザー詳細画面の表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector("h2"), 10);

		// タイトル確認
		assertEquals("ユーザー詳細", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 「詳細」ボタンを取得
		WebElement detailButton = webDriver.findElement(By.xpath("//input[@type='submit' and @value='詳細']"));

		// 「詳細」ボタン押下
		detailButton.click();

		// レポート詳細画面の表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".navbar-brand"), 10);

		// 修正した報告内容を取得
		WebElement reportContent = webDriver.findElement(
				By.xpath("//*[contains(text(), '報告内容を修正しました。')]"));

		// 修正内容が反映されていることを確認
		assertTrue(reportContent.isDisplayed());

		// レポート詳細画面のタイトル確認
		assertEquals("レポート詳細 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
