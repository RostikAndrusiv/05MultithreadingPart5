package org.rostik.andrusiv;

import com.google.gson.Gson;
import edu.umd.cs.mtc.MultithreadedTest;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.rostik.andrusiv.dao.AccountDao;
import org.rostik.andrusiv.entity.Account;
import org.rostik.andrusiv.entity.Currency;
import org.rostik.andrusiv.entity.CurrencyExchange;
import org.rostik.andrusiv.entity.CurrencyType;
import org.rostik.andrusiv.service.AccountService;
import org.rostik.andrusiv.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.rostik.andrusiv.entity.CurrencyType.UAH;
import static org.rostik.andrusiv.entity.CurrencyType.USD;

public class TestBase extends MultithreadedTest {

    protected AccountService accountService;

    protected AccountDao accountDao;

    protected static CurrencyExchange currencyExchange;

    private Gson gson = new Gson();

    @SneakyThrows
    @Override
    public void initialize() {
        accountService = new AccountService();
        accountDao = new AccountDao();
        Field baseDirField = accountDao.getClass()
                .getDeclaredField("baseDir");
        baseDirField.setAccessible(true);
        baseDirField.set(accountDao, "test/");

        Field daoField = accountService.getClass()
                .getDeclaredField("accountDao");
        daoField.setAccessible(true);

        daoField.set(accountService, accountDao);

        Account test = new Account("test");
        Currency UAH = new Currency(CurrencyType.UAH, BigDecimal.valueOf(985));
        test.setCurrencies(List.of(UAH));
        JsonUtils.saveJsonToFile(test, "test/exchange-service/accounts/test.json");
    }

    @Before
    public void setup() {
        currencyExchange = new CurrencyExchange(UAH, USD, BigDecimal.valueOf(0.15));
    }

    @After
    public void afterTest() throws IOException {
        Files.walk(Path.of("test/"))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
