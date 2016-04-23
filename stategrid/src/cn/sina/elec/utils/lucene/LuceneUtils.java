package cn.sina.elec.utils.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;

import cn.sina.elec.domain.ElecFileUpload;

public class LuceneUtils {

	/** 向索引库中添加数据 */
	public static void addIndex(ElecFileUpload fileUpload) {
		// 使用ElecFileUpload对象转换成Document对象
		Document document = FileUploadDocument.FileUploadToDocument(fileUpload);
		/** 新增、修改、删除、查询都会使用分词器 */
		try {
			IndexWriterConfig indexWirterConfig = new IndexWriterConfig(
					Version.LUCENE_36, Configuration.getAnalyzer());
			IndexWriter indexWriter = new IndexWriter(
					Configuration.getDirectory(), indexWirterConfig);
			// 添加数据
			indexWriter.addDocument(document);
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Author: 杨杰
	 * @CreateDate: 2015-12-2
	 * @Description: 通过projID,belongTo和关键字来获取lucene索引库的目录id
	 * @Version: V1.0.0
	 * @Params:
	 * @Return: List<ElecFileUpload>
	 */
	public static List<ElecFileUpload> searcherIndexByCondition(String projID,
			String belongTo, String queryString) {
		// 最后返回的List<ElecFileUpload>是从lucene的索引库中查询出来的
		List<ElecFileUpload> docList = new ArrayList<ElecFileUpload>();

		try {
			IndexSearcher indexSearcher = new IndexSearcher(
					IndexReader.open(Configuration.getDirectory()));
			// 接下来就是用这三个参数作为关键字在lucene的索引库中搜索了
			BooleanQuery query = new BooleanQuery();

			// Occur.MUST=>"and",Occur.SHOULD=>"or"
			//所属单位
			if (StringUtils.isNotBlank(projID)) {
				// 词条查询
				TermQuery query1 = new TermQuery(new Term("projId", projID));
				query.add(query1, Occur.MUST);
			}
			//图书类别
			if (StringUtils.isNotBlank(belongTo)) {
				TermQuery query2 = new TermQuery(new Term("belongTo", belongTo));
				query.add(query2, Occur.MUST);
			}
			//检索文字
			if (StringUtils.isNotBlank(queryString)) {
				// queryString是一个多字段的查询,我们输入的关键字可能是文件名,也可能是文件的描述
				// 以下是多字段查询的API
				QueryParser parser = new MultiFieldQueryParser(
						Version.LUCENE_36,
						new String[] { "fileName", "comment" },
						Configuration.getAnalyzer());
				Query query3 = parser.parse(queryString);
				query.add(query3, Occur.MUST);
			}

			// 获取到数据区里命中的Document
			TopDocs topDocs = indexSearcher.search(query, 100);// 100表示前100条搜索结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			//----------------设置文字高亮        begin-------------------
			//执行查询条件，因为高亮的值就是查询条件
			//html页面高亮显示的格式化，默认是<b></b>
			Formatter formatter = new SimpleHTMLFormatter("<font color='red'><b>","</b></font>");
			Scorer scorer = new QueryScorer(query);
			//高亮类,要给哪一个查询结果什么样的格式
			Highlighter highlighter = new Highlighter(formatter, scorer);
			
			//设置文字摘要，此时摘要大小
			int fragmentSize = 50;
			//摘要类
			Fragmenter fragmenter = new SimpleFragmenter(fragmentSize);
			highlighter.setTextFragmenter(fragmenter);
			//----------------设置文字高亮        end-------------------
			if (scoreDocs != null && scoreDocs.length > 0) {
				for (ScoreDoc scoreDoc : scoreDocs) {
					int docIndex = scoreDoc.doc;// 相当于获取到文档的角标,编号.
					// 通过编号获取到文档对象
					Document document = indexSearcher.doc(docIndex);
					// 再把文档对象转换成ElecFileUpload
					/**获取文字高亮的信息 begin*/
					//获取文字的高亮，一次只能获取一个字段高亮的结果，如果获取不到，返回null值
					//查找索引库字段为fileName
					String fileName = highlighter.getBestFragment(Configuration.getAnalyzer(), "fileName", document.get("fileName"));
					//如果null表示没有高亮的结果，如果高亮的结果，应该将原值返回
					if(StringUtils.isBlank(fileName)){
						fileName = document.get("fileName");
						if(fileName!=null && fileName.length()>fragmentSize){
							//截串，从0开始
							fileName = fileName.substring(0,fragmentSize);
						}
					}
					//将高亮后的结果放置到document中去
					document.getField("fileName").setValue(fileName);
					//查询索引库字段为comment
					String comment = highlighter.getBestFragment(Configuration.getAnalyzer(), "comment", document.get("comment"));
					//如果null表示没有高亮的结果，如果高亮的结果，应该将原值返回
					if(StringUtils.isBlank(comment)){
						comment = document.get("comment");
						if(comment!=null && comment.length()>fragmentSize){
							//截串，从0开始
							comment = comment.substring(0,fragmentSize);
						}
					}
					//将高亮后的结果放置到document中去
					document.getField("comment").setValue(comment);
					/**获取文字高亮的信息 end*/
					ElecFileUpload elecFileUpload = FileUploadDocument
							.documentToFileUpload(document);
					// 添加到返回结果的List
					docList.add(elecFileUpload);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return docList;
	}
}
