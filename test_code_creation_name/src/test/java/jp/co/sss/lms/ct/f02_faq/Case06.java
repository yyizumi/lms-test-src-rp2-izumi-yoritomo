package jp.co.sss.lms.ct.f02_faq;

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
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」メニューをクリック
		WebElement functionMenu = webDriver.findElement(By.linkText("機能"));
		functionMenu.click();

		// 「ヘルプ」をクリック
		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		// ヘルプ画面の表示確認
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「よくある質問」リンクをクリック
		WebElement faqLink = webDriver.findElement(By.linkText("よくある質問"));
		faqLink.click();

		// 別タブに切り替える
		String newTabs = webDriver.getWindowHandle();

		for (String window : webDriver.getWindowHandles()) {
			if (!window.equals(newTabs)) {
				webDriver.switchTo().window(window);
				break;
			}
		}

		// よくある質問画面の表示確認
		WebElement faqTitle = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", faqTitle.getText());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// 検索するカテゴリ
		String categoryName = "【研修関係】";

		// カテゴリリンクをクリック
		WebElement categoryLink = webDriver.findElement(By.linkText(categoryName));
		categoryLink.click();

		// 検索結果が表示されるまで待機
		WebDriverUtils.visibilityTimeout(By.cssSelector("tbody tr"), 10);

		// 検索結果を取得
		List<WebElement> results = webDriver.findElements(By.cssSelector("tbody tr"));

		// 検索結果が2件であることを確認
		assertEquals(2, results.size(), "検索結果の件数が想定と異なります。");

		// 検索結果の質問文を取得
		List<String> questions = results.stream().map(result -> result.findElement(By.cssSelector("dt")).getText())
				.toList();

		// 期待する質問が表示されていることを確認
		assertTrue(questions.contains("Q.キャンセル料・途中退校について"), "「キャンセル料・途中退校について」が表示されていません。");
		assertTrue(questions.contains("Q.研修の申し込みはどのようにすれば良いですか？"), "「研修の申し込みはどのようにすれば良いですか？」が表示されていません。");

		// よくある質問画面の表示確認
		// よくある質問画面のタイトル確認
		WebElement faqTitle = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", faqTitle.getText());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// 「キャンセル料・途中退校について」の質問を取得
		WebElement question = webDriver.findElement(By.xpath("//dt[contains(., 'キャンセル料・途中退校について')]"));

		// 質問をクリック
		// 質問に対応する回答を取得
		question.click();
		WebElement answer = question.findElement(By.xpath("./following-sibling::dd"));

		// 回答が表示されていることを確認
		// 回答内容が空ではないことを確認
		assertTrue(answer.isDisplayed(), "回答が表示されていません。");
		assertFalse(answer.getText().isEmpty(), "回答内容が表示されていません。");

		// よくある質問画面の表示確認
		WebElement faqTitle = webDriver.findElement(By.tagName("h2"));
		assertEquals("よくある質問", faqTitle.getText());
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
