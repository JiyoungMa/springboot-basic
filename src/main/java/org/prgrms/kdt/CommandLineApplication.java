package org.prgrms.kdt;

import org.prgrms.kdt.io.HowMuchDiscountMessage;
import org.prgrms.kdt.voucher.Validation;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.repository.MemoryVoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineApplication {
    public static void main(final String[] args) throws IOException, InterruptedException {
        final var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        final Scanner scanner = new Scanner(System.in);

        final List<Voucher> mylist = new ArrayList<Voucher>();

        boolean programRunning = true;
        String voucherType = "";

        do {
            System.out.println("=== Voucher Program ===");
            System.out.println("Type 'exit' for Exit.");
            System.out.println("Type 'create' to create a new voucher");
            System.out.println("Type 'list' to list all vouchers");

            final String commandInput = scanner.nextLine();

            switch (commandInput) {
                case "exit":
                    programRunning = false;
                    break;

                case "create":
                    boolean createTypeRunning = true;

                    System.out.println("=== Voucher Create ===");
                    System.out.println("Choose the type of voucher.");
                    System.out.println("- FixedAmountVoucher");
                    System.out.println("- PercentDiscountVoucher");

                    do {
                        voucherType = scanner.nextLine();
                        switch (voucherType) {
                            case "FixedAmountVoucher":
                                new HowMuchDiscountMessage(voucherType); // 할인값을 입력해달라는 메세지
                                mylist.add(MemoryVoucherRepository.create( // voucher 생성
                                        voucherType, Validation.inputValueValidation(voucherType))); // 유효한 값인지 검사
                                createTypeRunning = false;
                                break;

                            case "PercentDiscountVoucher":
                                new HowMuchDiscountMessage(voucherType); // 할인값을 입력해달라는 메세지
                                mylist.add(MemoryVoucherRepository.create( // voucher 생성
                                        voucherType, Validation.inputValueValidation(voucherType))); // 유효한 값인지 검사
                                createTypeRunning = false;
                                break;

                            default:
                                new HowMuchDiscountMessage(voucherType);
                                break;
                        }
                    } while (createTypeRunning);

                    break;

                case "list":
                    // list 목록 출력
                    for (int i = 0; i < mylist.size(); i++) {
                        System.out.println(mylist.get(i));
                    }
                    break;

                default:
                    System.out.println("=== Input type error ===");
                    System.out.println(MessageFormat.format("{0}은(는) 지원하지 않는 명령어입니다.", commandInput));
                    break;
            }
        } while (programRunning);
    }
}
