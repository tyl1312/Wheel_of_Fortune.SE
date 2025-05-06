package intro_softw_engi_groupb.wheel_of_fortune_web.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("VÒNG QUAY MAY MẮN 幸運の輪")
@Route("")
public class HomeView extends VerticalLayout {

    public HomeView() {

        add(new H1("VÒNG QUAY MAY MẮN 幸運の輪"));
        add(new Paragraph("This is the home view"));
        add(new Paragraph("You can edit this view in src\\main\\java\\intro_softw_engi_groupb\\wheel_of_fortune_web\\views\\HomeView.java"));

        var buttonJp = new Button("ここに押してください 🇯🇵");
        buttonJp.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("hellojp"));
        });
        add(buttonJp);

        var buttonEn = new Button("Click here 🇬🇧"); 
        buttonEn.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("helloen"));
        });
        add(buttonEn);

        var buttonVn = new Button("Nhấn vào đây 🇻🇳");
        buttonVn.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("hellovn"));
        });
        add(buttonVn);

        var buttonTest = new Button("Trang thử nghiệm");
        buttonTest.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("test"));
        });
        add(buttonTest);
    }
}
