package com.ndovel.ebook.service.impl;

import com.ndovel.ebook.exception.DataIsNotExistException;
import com.ndovel.ebook.model.dto.BookDTO;
import com.ndovel.ebook.model.dto.ChapterDTO;
import com.ndovel.ebook.model.dto.ContentDTO;
import com.ndovel.ebook.model.dto.MatchRexDTO;
import com.ndovel.ebook.model.dto.base.BaseDTO;
import com.ndovel.ebook.model.entity.*;
import com.ndovel.ebook.repository.*;
import com.ndovel.ebook.service.AsyncService;
import com.ndovel.ebook.service.SpiderService;
import com.ndovel.ebook.spider.bean.TaskCollection;
import com.ndovel.ebook.spider.core.NovelSpider;
import com.ndovel.ebook.spider.core.impl.CommonSpider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class SpiderServiceImpl implements SpiderService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MatchRexRepository matchRexRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AsyncService asyncService;

    @CacheEvict(cacheNames = {"book"}, allEntries = true)
    @Override
    public BookDTO spider(BookDTO bookDTO, String url, String encode, Integer matchRexDTOId) {
        MatchRex mr = matchRexRepository.findOneIsExist(matchRexDTOId)
                .orElseThrow(DataIsNotExistException::new);

        MatchRexDTO matchRex = new MatchRexDTO().init(mr);


        Book book = bookDTO.writeToDomain();
        Author author = authorRepository.findOneByName(book.getAuthor().getName());
        if(author == null) {
            author = book.getAuthor();
            authorRepository.save(author);
        }

        book.setAuthor(author);
        bookRepository.save(book);

        CommonSpider spider = new CommonSpider(book.getId(),url,matchRex);

        if ("GBK".equalsIgnoreCase(encode)) {
            spider.setEncode(CommonSpider.Encode.GBK);
        }

        asyncService.down(book, spider);

        return new BookDTO().init(book);
    }

    @Override
    public Map<String, String> spiderOne(String url, String encode, Integer matchRexDTOId) {
        MatchRex mr = matchRexRepository.findOneIsExist(matchRexDTOId)
                .orElseGet(()-> Optional.ofNullable(matchRexRepository.findAll())
                        .map(m->{
                            if(m.isEmpty())
                                return null;
                            else
                                return m.get(0);
                        }).orElseThrow(DataIsNotExistException::new));

        MatchRexDTO matchRex = new MatchRexDTO().init(mr);

        CommonSpider spider = new CommonSpider(0,url,matchRex);

        if ("GBK".equalsIgnoreCase(encode)) {
            spider.setEncode(CommonSpider.Encode.GBK);
        }

        spider.run();

        Map<String, String> map = new HashMap<>();

        String contentStr = Optional.ofNullable(spider.getContent())
                .map(ContentDTO::getInfo)
                .orElse("");

        map.put("content", contentStr);
        map.put("next", spider.getUrl());
        return map;
    }


}
