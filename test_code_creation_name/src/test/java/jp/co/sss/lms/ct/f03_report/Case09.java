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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
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
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「修正する」ボタンを取得
		WebElement editButton = webDriver.findElement(
				By.cssSelector("input[type='submit'][value='修正する']"));

		// 「修正する」ボタン押下
		editButton.click();

		// レポート登録画面の表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("form[action='/lms/report/complete']"), 10);

		// タイトル確認
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// 学習項目を未入力にする
		WebElement intFieldName = webDriver.findElement(By.id("intFieldName_0"));
		intFieldName.clear();

		// 提出するボタン
		WebElement submitButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

		// ボタンを画面中央へスクロール
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				submitButton);

		// 提出する
		submitButton.click();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".errorInput"), 10);

		// 画面再描画後に、学習項目を再取得
		intFieldName = webDriver.findElement(By.id("intFieldName_0"));

		// 学習項目のエラー確認
		assertTrue(intFieldName.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// 学習項目を入力
		WebElement intFieldName = webDriver.findElement(
				By.id("intFieldName_0"));
		intFieldName.clear();
		intFieldName.sendKeys("Javaの基礎");

		// 理解度を未入力にする
		WebElement intFieldValue = webDriver.findElement(
				By.id("intFieldValue_0"));

		Select select = new Select(intFieldValue);
		select.selectByValue("");

		// 提出するボタン
		WebElement submitButton = webDriver.findElement(
				By.cssSelector("button[type='submit']"));

		// ボタンを画面中央へスクロール
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				submitButton);

		// 提出する
		submitButton.click();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".errorInput"), 10);

		// 画面再描画後に再取得
		intFieldValue = webDriver.findElement(
				By.id("intFieldValue_0"));

		// 理解度のエラー確認
		assertTrue(intFieldValue.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// 目標の達成度を数値以外にする
		WebElement achievement = webDriver.findElement(By.id("content_0"));
		achievement.clear();
		achievement.sendKeys("abc");

		// 提出する
		WebElement submitButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();",
				submitButton);

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".errorInput"), 10);

		// 画面再描画後に目標の達成度を再取得
		achievement = webDriver.findElement(By.id("content_0"));

		// 目標の達成度のエラー確認
		assertTrue(achievement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// 目標の達成度を範囲外にする
		WebElement achievement = webDriver.findElement(By.id("content_0"));
		achievement.clear();
		achievement.sendKeys("11");

		// 提出する
		WebElement submitButton = webDriver.findElement(By.cssSelector("button[type='submit']"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();",
				submitButton);

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".errorInput"), 10);

		// 画面再描画後に目標の達成度を再取得
		achievement = webDriver.findElement(By.id("content_0"));

		// 目標の達成度のエラー確認
		assertTrue(achievement.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// 目標の達成度を未入力にする
		WebElement achievement = webDriver.findElement(
				By.id("content_0"));
		achievement.clear();

		// 所感を未入力にする
		WebElement impression = webDriver.findElement(
				By.id("content_1"));
		impression.clear();

		// 提出する
		WebElement submitButton = webDriver.findElement(
				By.cssSelector("button[type='submit']"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();",
				submitButton);

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector(".errorInput"), 10);

		// 画面再描画後に再取得
		achievement = webDriver.findElement(
				By.id("content_0"));

		impression = webDriver.findElement(
				By.id("content_1"));

		// 目標の達成度のエラー確認
		assertTrue(
				achievement.getAttribute("class").contains("errorInput"));

		// 所感のエラー確認
		assertTrue(
				impression.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// 2001文字の文字列を作成
		String over2000 = "あ".repeat(2001);

		// 所感を2000文字超にする
		WebElement impression = webDriver.findElement(
				By.id("content_1"));
		impression.clear();
		impression.sendKeys(over2000);

		// 一週間の振り返りを2000文字超にする
		WebElement weeklyReview = webDriver.findElement(
				By.id("content_2"));
		weeklyReview.clear();
		weeklyReview.sendKeys(over2000);

		// 提出する
		WebElement submitButton = webDriver.findElement(
				By.cssSelector("button[type='submit']"));

		// JavaScriptでクリック
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].click();",
				submitButton);

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector(".errorInput"), 10);

		// 画面再描画後に再取得
		impression = webDriver.findElement(
				By.id("content_1"));

		weeklyReview = webDriver.findElement(
				By.id("content_2"));

		// 所感のエラー確認
		assertTrue(
				impression.getAttribute("class").contains("errorInput"));

		// 一週間の振り返りのエラー確認
		assertTrue(
				weeklyReview.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
