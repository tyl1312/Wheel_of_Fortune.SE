package intro_softw_engi_groupb.wheel_of_fortune_web.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import intro_softw_engi_groupb.wheel_of_fortune_web.data.Reward;
import intro_softw_engi_groupb.wheel_of_fortune_web.services.CrmService;

@PageTitle("Test Page")
@Route("test")
public class TestPageView extends VerticalLayout{
    private Grid<Reward> grid = new Grid<>(Reward.class);
    private TextField filterText = new TextField();
    private CrmService crmService;
    
    public TestPageView(CrmService crmService) {
        this.crmService = crmService;
        
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        var buttonHome = new Button("Quay lại trang chủ");
        buttonHome.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        createGrid();

        updateGrid();

        add(new com.vaadin.flow.component.html.H1("TRANG THỬ NGHIỆM"), 
            buttonHome, createFilter(), grid);

    }

    private Component createFilter() {
        filterText.setPlaceholder("Tìm kiếm...");
        filterText.setClearButtonVisible(true);

        Button searchButton = new Button("Tìm kiếm");
        searchButton.addClickListener(event -> updateGrid());

        HorizontalLayout searchLayout = new HorizontalLayout(filterText, searchButton);
        
        return searchLayout;
    }

    private void updateGrid() {
        grid.setItems(crmService.searchRewards(filterText.getValue()));
    }

    private void createGrid() {
        grid.setSizeFull();
        grid.setColumns("rewardId", "rewardName", "redeemPoints");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
