package cn.graydove.server.api;

import cn.graydove.server.model.request.BookRequest;
import cn.graydove.server.model.request.ChapterRequest;
import cn.graydove.server.model.vo.ChapterVO;
import cn.graydove.server.service.BookService;
import cn.graydove.server.service.ChapterService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author graydove
 */
@DubboService
@AllArgsConstructor
public class BookWriteFacadeImpl implements BookWriteFacade {

    private BookService bookService;

    private ChapterService chapterService;

    @Override
    public Long createBook(BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @Override
    public Long appendChapter(ChapterRequest chapterRequest) {
        return chapterService.appendChapter(chapterRequest);
    }

}