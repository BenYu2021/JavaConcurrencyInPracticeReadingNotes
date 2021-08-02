package ch06;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author: ymm
 * @date: 2021/8/2
 * @version: 1.0.0
 * @description: 6-17 在预定时间内请求旅游报价
 * solicit    英[səˈlɪsɪt] 美[səˈlɪsɪt]
 * v.	索求，请求…给予(援助、钱或信息); 征求; 筹集; 招徕(嫖客); 拉(客);
 */
public class TimeBudget {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> raking, long time, TimeUnit unit) throws InterruptedException {
        ArrayList<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }

        // 支持限时的invokeAll
        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

        ArrayList<TravelQuote> quotes = new ArrayList<>(tasks.size());

        Iterator<QuoteTask> taskIterator = tasks.iterator();

        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIterator.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeOutQuote(e));
            }
        }

        Collections.sort(quotes, raking);
        return quotes;

    }
}

class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable t) {
        return null;
    }

    TravelQuote getTimeOutQuote(CancellationException e) {
        return null;
    }

    @Override
    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }
}

// 旅游公司
interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

// 旅游请求
interface TravelQuote {

}

// 旅游信息
interface TravelInfo {

}