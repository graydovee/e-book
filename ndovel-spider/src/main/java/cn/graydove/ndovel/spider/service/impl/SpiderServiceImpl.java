package cn.graydove.ndovel.spider.service.impl;

import cn.graydove.ndovel.common.oss.OssTemplate;
import cn.graydove.ndovel.common.response.Response;
import cn.graydove.ndovel.server.api.facade.NovelFacade;
import cn.graydove.ndovel.server.api.model.request.*;
import cn.graydove.ndovel.spider.model.dto.*;
import cn.graydove.ndovel.spider.service.SpiderService;
import cn.graydove.ndovel.server.api.facade.BookWriteFacade;
import cn.graydove.ndovel.server.api.enums.PublishStatus;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author graydove
 */
@Slf4j
@Service
public class SpiderServiceImpl implements SpiderService {

    @DubboReference
    private BookWriteFacade bookWriteFacade;

    @DubboReference
    private NovelFacade novelFacade;

    @Autowired
    private OssTemplate ossTemplate;

    @Override
    public void spider(BookDTO bookDTO) {
        BookRequest bookRequest = BeanUtil.toBean(bookDTO, BookRequest.class);
        bookRequest.setStatus(PublishStatus.RELEASE);
        Long bookId = bookWriteFacade.createBook(bookRequest);
        for (int i=0; i < 25; ++i) {
            ChapterRequest chapterRequest = new ChapterRequest();
            chapterRequest.setBookId(bookId);
            chapterRequest.setTitle("测试标题" + i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i);
            for (int j=0;j<10;++j) {
                stringBuilder.append(stringBuilder);
            }
            chapterRequest.setContent(stringBuilder.toString());
            chapterRequest.setStatus(PublishStatus.RELEASE);
            bookWriteFacade.appendChapter(chapterRequest);
        }
    }

    @Override
    public Boolean deleteBook(BookDeleteDTO bookDeleteDTO) {
        BookDeleteRequest bookDeleteRequest = new BookDeleteRequest();
        bookDeleteRequest.setBookId(bookDeleteDTO.getId());
        return bookWriteFacade.deleteBook(bookDeleteRequest);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {
        BookRequest bookRequest = BeanUtil.toBean(bookDTO, BookRequest.class);
        bookRequest.setStatus(PublishStatus.RELEASE);
        Long id = bookWriteFacade.createBook(bookRequest);
        try {
            uploadCover(bookDTO.getCover(), id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return id;
    }

    @Override
    public void createChapter(ChapterDTO chapterDTO) {
        ChapterRequest chapterRequest = BeanUtil.toBean(chapterDTO, ChapterRequest.class);
        chapterRequest.setStatus(PublishStatus.RELEASE);
        bookWriteFacade.appendChapter(chapterRequest);
    }

    private void uploadCover(String url, Long bookId) {
        Response<String> coverUrl = ossTemplate.upload(url, "cover/spider_cover_" + bookId);
        if (coverUrl.getSuccess() && StrUtil.isNotBlank(coverUrl.getResult())) {
            UpdateBookRequest request = new UpdateBookRequest();
            request.setId(bookId);
            request.setCover(coverUrl.getResult());
            bookWriteFacade.updateBook(request);
        }
    }
}
