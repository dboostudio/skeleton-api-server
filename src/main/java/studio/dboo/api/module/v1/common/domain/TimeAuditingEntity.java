package studio.dboo.api.module.v1.common.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TimeAuditingEntity
 * - Entity에 상속하여 생성일시, 수정일시를 제공한다.
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //Auditing
public abstract class TimeAuditingEntity {

//    @Column(updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;
}
