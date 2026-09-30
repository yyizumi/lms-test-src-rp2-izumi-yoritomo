package jp.co.sss.lms.ct.f06_login2;

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
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// ユーザーID入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAB02");

		// パスワード入力
		webDriver.findElement(By.id("password")).sendKeys("StudentAB02");

		// ログインボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit'], input[type='submit']")).click();

		// 利用規約画面への遷移を確認
		WebDriverUtils.visibilityTimeout(By.cssSelector("h2"), 10);

		assertEquals("利用規約", webDriver.findElement(By.tagName("h2")).getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		// 「同意します」にチェックを入れる
		WebElement checkbox = webDriver.findElement(
				By.cssSelector("input[type='checkbox'][name='securityFlg']"));

		checkbox.click();

		// チェックされていることを確認
		assertTrue(checkbox.isSelected());

		// 「次へ」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// パスワード変更画面への遷移を確認
		WebDriverUtils.visibilityTimeout(By.tagName("h2"), 10);

		assertEquals("パスワード変更", webDriver.findElement(By.tagName("h2")).getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		// パスワードを未入力にする
		webDriver.findElement(By.id("currentPassword")).clear();
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("passwordConfirm")).clear();

		// 「変更」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 確認モーダルの「変更」ボタンを押下
		WebDriverUtils.visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".help-inline.error"), 10);

		// ★送信後に要素を再取得する
		WebElement errorCurrentPassword = webDriver.findElement(By.id("currentPassword"));
		WebElement errorPassword = webDriver.findElement(By.id("password"));
		WebElement errorPasswordConfirm = webDriver.findElement(By.id("passwordConfirm"));

		// エラークラスを確認
		assertTrue(errorCurrentPassword.getAttribute("class").contains("errorInput"));
		assertTrue(errorPassword.getAttribute("class").contains("errorInput"));
		assertTrue(errorPasswordConfirm.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		// 現在のパスワードを入力
		webDriver.findElement(By.id("currentPassword"))
				.sendKeys("StudentAB02");

		// 20文字以上の新しいパスワードを入力
		webDriver.findElement(By.id("password"))
				.sendKeys("Aa12345678901234567890");

		// 確認パスワードを入力
		webDriver.findElement(By.id("passwordConfirm"))
				.sendKeys("Aa12345678901234567890");

		// 「変更」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 確認モーダルの「変更」ボタンを押下
		WebDriverUtils.visibilityTimeout(By.id("upd-btn"), 10);

		webDriver.findElement(By.id("upd-btn")).click();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector(".help-inline.error"), 10);

		// 新しいパスワードにエラーが付いていることを確認
		assertTrue(webDriver.findElement(By.id("password")).getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		// 現在のパスワード入力
		webDriver.findElement(By.id("currentPassword"))
				.sendKeys("StudentAB02");

		// ポリシー違反の新しいパスワードを入力
		// 英大文字を含まない
		webDriver.findElement(By.id("password"))
				.sendKeys("student123");

		// 確認パスワード入力
		webDriver.findElement(By.id("passwordConfirm"))
				.sendKeys("student123");

		// 「変更」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 確認モーダルの「変更」ボタンを押下
		WebDriverUtils.visibilityTimeout(By.id("div-modal"), 10);
		webDriver.findElement(By.id("upd-btn")).click();

		// 入力チェックエラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector("span.help-inline.error"), 10);

		// 新しいパスワードにエラークラスが付いていることを確認
		WebElement password = webDriver.findElement(By.id("password"));
		assertTrue(password.getAttribute("class").contains("errorInput"));

		// エラーメッセージが表示されていることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		assertTrue(errorMessage.isDisplayed());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		// 現在のパスワード入力
		webDriver.findElement(By.id("currentPassword"))
				.sendKeys("StudentAB02");

		// 新しいパスワード入力
		webDriver.findElement(By.id("password"))
				.sendKeys("StudentAA041");

		// 確認パスワードは別の値を入力
		webDriver.findElement(By.id("passwordConfirm"))
				.sendKeys("StudentAA042");

		// 「変更」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']"))
				.click();

		// 確認モーダルを表示
		WebDriverUtils.visibilityTimeout(By.id("div-modal"), 10);

		// モーダルの「変更」ボタンを押下
		webDriver.findElement(By.id("upd-btn")).click();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(By.cssSelector("span.help-inline.error"), 10);

		// 確認パスワードにエラークラスが付いていることを確認
		WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));
		assertTrue(passwordConfirm.getAttribute("class").contains("errorInput"));

		// エラーメッセージが表示されていることを確認
		WebElement errorMessage = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		assertTrue(errorMessage.isDisplayed());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
