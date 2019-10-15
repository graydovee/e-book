package com.ndovel.ebook.spider.core.impl;

import com.ndovel.ebook.model.dto.BookDTO;
import com.ndovel.ebook.model.dto.MatchRexDTO;
import com.ndovel.ebook.model.dto.SpiderInfoDTO;
import com.ndovel.ebook.model.dto.TempChapter;
import com.ndovel.ebook.spider.core.AbstractNovelSpider;
import com.ndovel.ebook.utils.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonNovelSpider extends AbstractNovelSpider {
    private MatchRexDTO matchRex;

    public CommonNovelSpider(SpiderInfoDTO spiderInfoDTO) {
        super(Optional.ofNullable(spiderInfoDTO.getBook()).map(BookDTO::getId).orElse(null), spiderInfoDTO.getUrl());
        this.matchRex = spiderInfoDTO.getMatchRex();
    }

    @Override
    protected String getContentFormCode(String code) {
        String content =  match(code, matchRex.getContentRex());
        if (content == null)
            return null;
        return StringUtils.formatContent(content);

    }

    @Override
    protected String getTitleFormCode(String code) {
        return match(code, matchRex.getTitleRex());
    }

    @Override
    protected String getNextPageFormCode(String code) {
        return match(code, matchRex.getNextPageRex());
    }

    private String match(String code, String rule) {
        Pattern nextPagePattern = Pattern.compile(rule);
        Matcher nextPageMarcher = nextPagePattern.matcher(code);
        if (nextPageMarcher.find()) {
            return nextPageMarcher.group(1);
        }
        return null;
    }

    @Override
    public TempChapter getTempChapter() {
        TempChapter tempChapter = null;
        if (content != null) {
            tempChapter = new TempChapter();
            tempChapter.setContent(content.getInfo());
            tempChapter.setUrl(url);
        }
        return tempChapter;
    }
}
