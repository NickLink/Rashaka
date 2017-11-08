package com.rashaka.fragments.main.home.weight.year;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface YearView {

    void setValues(String one, String two, String three);

    void setGraphData(List<Double> wList, List<String> dList);

    void setEmptyView();
}
