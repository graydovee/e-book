package cn.graydove.server.model.dto;

import cn.graydove.common.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author graydove
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChapterPageDTO extends PageQuery {
    private static final long serialVersionUID = -5222677835449633764L;

    @NotNull
    private Long bookId;
}