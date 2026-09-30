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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		// 出勤（時）を空白にする
		Select startHour = new Select(
				webDriver.findElement(By.id("startHour0")));
		startHour.selectByValue("");

		// 更新ボタンを押下
		WebElement updateButton = webDriver.findElement(
				By.cssSelector("input.update-button"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				updateButton);

		updateButton.click();

		// 確認ダイアログでOK
		webDriver.switchTo().alert().accept();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector(".errorInput"), 10);

		// 更新後に要素を再取得する
		WebElement errorStartHour = webDriver.findElement(
				By.id("startHour0"));

		// 出勤（時）にエラークラスが付いていることを確認
		assertTrue(
				errorStartHour.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		// 出勤（時）を空白にする
		Select startHour = new Select(
				webDriver.findElement(By.id("startHour0")));
		startHour.selectByValue("");

		// 出勤（分）も空白にする
		Select startMinute = new Select(
				webDriver.findElement(By.id("startMinute0")));
		startMinute.selectByValue("");

		// 退勤を入力する
		Select endHour = new Select(
				webDriver.findElement(By.id("endHour0")));
		endHour.selectByValue("18");

		Select endMinute = new Select(
				webDriver.findElement(By.id("endMinute0")));
		endMinute.selectByValue("0");

		// 更新ボタンを押下
		WebElement updateButton = webDriver.findElement(
				By.cssSelector("input.update-button"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				updateButton);

		updateButton.click();

		// 確認ダイアログでOK
		webDriver.switchTo().alert().accept();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector(".errorInput"), 10);

		// 更新後に要素を再取得
		WebElement errorStartHour = webDriver.findElement(
				By.id("startHour0"));

		WebElement errorStartMinute = webDriver.findElement(
				By.id("startMinute0"));

		// 出勤（時・分）にエラークラスが付いていることを確認
		assertTrue(
				errorStartHour.getAttribute("class").contains("errorInput"));

		assertTrue(
				errorStartMinute.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		// 出勤を18:00にする
		Select startHour = new Select(
				webDriver.findElement(By.id("startHour0")));
		startHour.selectByValue("18");

		Select startMinute = new Select(
				webDriver.findElement(By.id("startMinute0")));
		startMinute.selectByValue("0");

		// 退勤を09:00にする
		Select endHour = new Select(
				webDriver.findElement(By.id("endHour0")));
		endHour.selectByValue("9");

		Select endMinute = new Select(
				webDriver.findElement(By.id("endMinute0")));
		endMinute.selectByValue("0");

		// 更新ボタンを押下
		WebElement updateButton = webDriver.findElement(
				By.cssSelector("input.update-button"));

		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView({block: 'center'});",
				updateButton);

		updateButton.click();

		// 確認ダイアログでOK
		webDriver.switchTo().alert().accept();

		// エラー表示を待機
		WebDriverUtils.visibilityTimeout(
				By.cssSelector(".errorInput"), 10);

		// 更新後に要素を再取得
		WebElement errorStartHour = webDriver.findElement(
				By.id("startHour0"));

		WebElement errorStartMinute = webDriver.findElement(
				By.id("startMinute0"));

		WebElement errorEndHour = webDriver.findElement(
				By.id("endHour0"));

		WebElement errorEndMinute = webDriver.findElement(
				By.id("endMinute0"));

		// 出勤・退勤の時分にエラークラスが付いていることを確認
		WebElement error = webDriver.findElement(
				By.cssSelector(".help-inline.error"));

		assertTrue(error.isDisplayed());

		assertTrue(
				errorStartMinute.getAttribute("class").contains("errorInput"));

		assertTrue(
				errorEndHour.getAttribute("class").contains("errorInput"));

		assertTrue(
				errorEndMinute.getAttribute("class").contains("errorInput"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		// TODO ここに追加
	}

}
