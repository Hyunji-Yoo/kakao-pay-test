# kakao-pay-test
카카오페이 사전과제 제출을 위한 레포지토리


## 개발 환경 ##
* 사용 언어: JAVA
* 프레임워크: SpringBoot
* DB: H2


## 테이블 설계 ##
<ul><li>결제</li></ul>
* 테이블명 : payment
<ul>    
<li>management_no bigint not null                </li>
<li>amount bigint not null                       </li>
<li>canceled varchar(1)                          </li>
<li>payment_card_no varchar(255)                 </li>
<li>payment_cvc varchar(255)                     </li>
<li>payment_encrypted_value varchar(255)         </li>
<li>payment_expiry_date varchar(255)             </li>
<li>installment_plan varchar(2) not null         </li>
<li>original_management_no varchar(20)           </li>
<li>payment_type varchar(10) not null            </li>
<li>reserve varchar(47)                          </li>
<li>vat bigint not null                          </li>
<li>primary key (management_no)                   </li>
</ul>    
<ul><li>결제인터페이스</li></ul>
* 테이블명 : payment_interface
<ul>
<li>interface_number bigint not null         </li>
<li>body varchar(416)                        </li>
<li>entire_data varchar(450)                 </li>
<li>header varchar(34)                       </li>
<li>management_no bigint                     </li>
<li>payment_type varchar(10) not null        </li>
<li>primary key (interface_number)           </li>
</ul>


## 문제해결 전략 ##
* 결제요청과 결제취소를 모두 payment에 데이터를 저장하되 구분값(payment_type)으로 분별할 수 있게 한다.  
* 중복결제 방지를 위해 플래그(canceled) 컬럼을 둔다.


## 민감정보 암호화 ##
* 대상데이터: 카드번호|유효기간|cvc
* 암호 알고리즘: AES/ECB/PKCS5Padding


## 빌드 및 실행 방법 ##
src/test/java의 com.kakaopay.test.controller.PaymentControllerTest.executeAndCancelPaymentTest를 단위테스트(jUnit)로 실행한다.
