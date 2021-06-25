package bb.study.dbi.Random;

import java.util.List;

public class RandomOrder {
    public String month;
    public int cid;
    public int aid;
    public int pid;
    public int qty;
    public float dollars;

    private final String[] months = {"jan", "feb", "mar"};

    public RandomOrder(List<Integer> pCids, List<Integer> pAids, List<Integer> pPids) {
        this.month = months[(int) (Math.random() * months.length)];;
        this.cid = pCids.get((int) (Math.random() * pCids.size()));
        this.aid = pAids.get((int) (Math.random() * pAids.size()));
        this.pid = pPids.get((int) (Math.random() * pPids.size()));
        this.qty = (int) (Math.random() * 500);
        this.dollars = (float) (Math.round((Math.random() * 10000) * 100.0) / 100.0);
    }
}
