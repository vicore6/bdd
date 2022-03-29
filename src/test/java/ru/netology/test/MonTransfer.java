package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelp;
import ru.netology.page.DashboardCardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonTransfer {

    @Test
    void shouldTransferMoneyBetweenOwnCardsFrom2To_1() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelp.getAuthInf();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelp.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        String sumRep = "500";  //сумма пополнения
        val balance_1_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса 2 карты до перевода
        val dashboardCardReplPage = dashboardCardPage.replenishCard(DataHelp.getCard_1().getNumber()); //выбор карты для пополнения
        dashboardCardReplPage.replenishCard(sumRep, DataHelp.getCard_2().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса
        int sum_Rep = Integer.parseInt(sumRep);
        assertEquals(balance_1_CardBefoRep + sum_Rep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep - sum_Rep, balance_2_CardAfteRep);
    }


    @Test
    void shouldTransferMoneyBetweenOwnCardsFrom1To_2() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelp.getAuthInf();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelp.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        String sumRep = "500";  //сумма пополнения
        val balance_1_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса 2 карты до перевода
        val dashboardCardReplPage = dashboardCardPage.replenishCard(DataHelp.getCard_2().getNumber()); //выбор карты для пополнения
        dashboardCardReplPage.replenishCard(sumRep, DataHelp.getCard_1().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса
        int sum_Rep = Integer.parseInt(sumRep);
        assertEquals(balance_1_CardBefoRep - sum_Rep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep + sum_Rep, balance_2_CardAfteRep);
    }


    @Test
    void shouldDepositAmountExceedsActualAmount() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelp.getAuthInf();      //получение логина и пароля
        var verificationPage = loginPage.validLogin(authInfo);   //ввод данных
        var verificationCode = DataHelp.getVerificationCodeFor(authInfo);  //получение кода СМС
        var dashboardCardPage = verificationPage.validVerify(verificationCode);  //ввод смс
        val balance_1_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса 1 карты до перевода
        val balance_2_CardBefoRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса 2 карты до перевода
        int sumRepPlus=balance_1_CardBefoRep+500;// получение суммы превышающей остаток на счету
        val dashboardCardReplPage = dashboardCardPage.replenishCard(DataHelp.getCard_2().getNumber()); //выбор карты для пополнения
        dashboardCardReplPage.replenishCard(String.valueOf(sumRepPlus), DataHelp.getCard_1().getNumber());  //ввод данных
        val balance_1_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_1().getNumber());  //получение баланса
        val balance_2_CardAfteRep = dashboardCardPage.infoBalansCard(DataHelp.getCard_2().getNumber());  //получение баланса
        assertEquals(balance_1_CardBefoRep, balance_1_CardAfteRep);
        assertEquals(balance_2_CardBefoRep, balance_2_CardAfteRep);
    }

}