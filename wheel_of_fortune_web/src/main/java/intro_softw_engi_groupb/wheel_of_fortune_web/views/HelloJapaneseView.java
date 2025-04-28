package intro_softw_engi_groupb.wheel_of_fortune_web.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("こんにちは!")
@Route("/hellojp")
public class HelloJapaneseView extends VerticalLayout {

    public HelloJapaneseView() {
        add(new H1("こんにちは、世界！ 🇯🇵"));

        var buttonHome = new Button("ホームページへ戻る");
        buttonHome.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        add(buttonHome);
    }
    
}
