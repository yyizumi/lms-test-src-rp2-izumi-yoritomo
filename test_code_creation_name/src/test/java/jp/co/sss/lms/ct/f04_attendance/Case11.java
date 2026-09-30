package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		// 「勤怠情報を直接編集する」リンクを取得
		WebElement attendanceUpdateLink = webDriver.findElement(
				By.cssSelector("a[href='/lms/attendance/update']"));

		// リンク押下
		attendanceUpdateLink.click();

		// 勤怠情報直接変更画面の表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("h2"), 10);

		// タイトル確認
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());

		// 勤怠管理画面であることを確認
		assertEquals("勤怠管理", webDriver.findElement(By.cssSelector("h2")).getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		// すべての研修日程の「定時」ボタンを押下
		List<WebElement> defaultButtons = webDriver.findElements(
				By.cssSelector("button.default-button"));

		JavascriptExecutor js = (JavascriptExecutor) webDriver;

		for (WebElement button : defaultButtons) {
			js.executeScript("arguments[0].click();", button);
		}

		// 「更新」ボタンを押下
		WebElement updateButton = webDriver.findElement(
				By.cssSelector("input.update-button"));

		// 更新ボタンを画面中央までスクロール
		js.executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				updateButton);

		updateButton.click();

		// 確認ダイアログでOK
		webDriver.switchTo().alert().accept();

		// 勤怠管理画面への遷移を確認
		WebDriverUtils.visibilityTimeout(
				By.cssSelector("table.dataTable"), 10);

		assertEquals(
				"勤怠管理",
				webDriver.findElement(By.tagName("h2")).getText());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
