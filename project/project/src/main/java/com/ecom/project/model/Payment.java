package com.ecom.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;


    @OneToOne(mappedBy = "payment" , cascade ={ CascadeType.PERSIST,CascadeType.MERGE})
    private Order order;

    @NotBlank
    @Size(min = 4 , message ="Payment method must  contain at least 4  characters")
    private String paymentMethod;

    private String  pgPaymentId;
    private  String pgStatus;
    private  String pgResponseMessage;
    private String pgName;


    public Payment(Long paymentId, String pgName, String pgResponseMessage, String pgStatus, String pgPaymentId) {
        this.paymentId = paymentId;
        this.pgName = pgName;
        this.pgResponseMessage = pgResponseMessage;
        this.pgStatus = pgStatus;
        this.pgPaymentId = pgPaymentId;
    }
}
