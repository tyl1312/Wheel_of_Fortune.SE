package intro_softw_engi_groupb.wheel_of_fortune_web.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Xin chào!")
@Route("/hellovn")
public class HelloVietnamese extends VerticalLayout {

    public HelloVietnamese() {
        add(new H1("Xin chào thế giới! 🇻🇳"));

        var buttonHome = new Button("Quay lại trang chủ");
        buttonHome.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        add(buttonHome);
    }
    
}
