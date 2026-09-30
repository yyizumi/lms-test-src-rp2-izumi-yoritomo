package jp.co.sss.lms.ct.f04_attendance;

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
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		// 「勤怠」リンクを取得
		WebElement attendanceLink = webDriver.findElement(
				By.cssSelector("a[href='/lms/attendance/detail']"));

		// 「勤怠」リンク押下
		attendanceLink.click();

		// 勤怠管理画面の表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("h2"), 10);

		// タイトル確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		// 「出勤」ボタンを取得
		WebElement punchInButton = webDriver.findElement(
				By.cssSelector("input[name='punchIn']"));

		// 「出勤」ボタン押下
		punchInButton.click();

		// 確認ダイアログでOKを押下
		webDriver.switchTo().alert().accept();

		// 勤怠管理画面の表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("h2"), 10);

		// 勤怠管理画面のタイトル確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		// 「出勤」ボタンを取得
		WebElement punchInButton = webDriver.findElement(
				By.cssSelector("input[name='punchIn']"));

		// 「出勤」ボタン押下
		punchInButton.click();

		// 確認ダイアログでOKを押下
		webDriver.switchTo().alert().accept();

		// 勤怠管理画面の表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("h2"), 10);

		// 勤怠管理画面のタイトル確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
