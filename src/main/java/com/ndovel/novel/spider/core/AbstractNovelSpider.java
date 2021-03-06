package com.ndovel.novel.spider.core;


import com.ndovel.novel.model.dto.ChapterDTO;
import com.ndovel.novel.model.dto.ContentDTO;
import com.ndovel.novel.model.dto.TempChapter;
import com.ndovel.novel.spider.util.UrlUtils;
import com.ndovel.novel.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class AbstractNovelSpider extends AbstractSpider implements NovelSpider {
    protected String url;
    protected boolean urlUes = false;

    protected Integer bookId;
    protected ChapterDTO chapter;
    protected ContentDTO content;

    @Override
    public String getUrl() {
        return url;
    }

    protected AbstractNovelSpider(Integer bookId, String firstPageUrl) {
        this.bookId = bookId;

        this.url = UrlUtils.urlFormat(firstPageUrl);
    }


    protected abstract String getContentFormCode(String code);

    protected abstract String getTitleFormCode(String code);

    protected abstract String getNextPageFormCode(String code);

    @Override
    public boolean hasNext() {
        return !StringUtils.isEmpty(url) && !isUrlUes();
    }

    public void setNewUrl(String url){
        this.url = url;
        urlUes = false;
    }

    @Override
    public void run() {
        this.content = null;
        this.chapter = null;
        if(this.urlUes){
            return;
        }

        this.urlUes = true;

        //获取页面源代码
        String code = sendHttpGetRequest(url);

        if (code == null) {
            return;
        }

        //获取正文内容
        String content = getContentFormCode(code);

        if (StringUtils.isEmpty(content)) {
            return;
        }

        //重新初始化
        this.content = new ContentDTO();
        this.chapter = new ChapterDTO();
        this.chapter.setBookId(bookId);


        this.content.setInfo(content);

        this.chapter.setTitle(getTitleFormCode(code));

        String nextPage = getNextPageFormCode(code);

        if(!StringUtils.isEmpty(nextPage)){
            setNewUrl(UrlUtils.jump(url, nextPage));
        }


    }

    @Override
    public TempChapter getTempChapter() {
        TempChapter tempChapter = null;
        if (content != null) {
            tempChapter = new TempChapter();
            tempChapter.setContent(content.getInfo());
            tempChapter.setUrl(url);
            tempChapter.setTitle(chapter.getTitle());
        }
        return tempChapter;
    }
}
