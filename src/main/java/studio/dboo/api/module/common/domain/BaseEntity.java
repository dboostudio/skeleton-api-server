package studio.dboo.api.module.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @ApiModelProperty("생성일시")
    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @ApiModelProperty("수정일시")
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
