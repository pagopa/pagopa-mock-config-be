package it.gov.pagopa.mockconfig.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "rule_tag")
public class RuleTagEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "value")
    private String value;

    @ManyToMany(targetEntity = MockRuleEntity.class)
    @JoinTable(name = "mock_rule_tag",
            joinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "mock_rule_id", referencedColumnName = "id")})
    private List<MockRuleEntity> rules;
}
