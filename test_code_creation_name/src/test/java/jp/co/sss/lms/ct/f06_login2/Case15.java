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
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

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
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA04");

		// パスワード入力
		webDriver.findElement(By.id("password")).sendKeys("StudentAA04");

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
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		// 「同意します」にチェックを入れない
		WebElement securityCheckbox = webDriver
				.findElement(By.cssSelector("input[type='checkbox'][name='securityFlg']"));

		assertFalse(securityCheckbox.isSelected());

		// 「次へ」ボタンを押下
		webDriver.findElement(By.cssSelector("button[type='submit']")).click();

		// 利用規約画面に戻っていることを確認
		WebDriverUtils.visibilityTimeout(By.cssSelector("div.error"), 10);

		assertEquals(
				"利用規約",
				webDriver.findElement(By.tagName("h2")).getText());

		// エラーメッセージ確認
		WebElement errorMessage = webDriver.findElement(
				By.cssSelector("div.error"));

		assertEquals("セキュリティ規約への同意は必須です。", errorMessage.getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
