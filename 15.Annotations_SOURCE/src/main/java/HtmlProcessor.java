import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm"})
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотаций HtmlForm
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            // получаем полный путь для генерации html
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // User.class -> User.html
            Form form = new Form();
            path = path.substring(1) + element.getSimpleName().toString() + ".ftlh";
            Path out = Paths.get(path);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
                HtmlForm annotation = element.getAnnotation(HtmlForm.class);
                form.setAction(annotation.action());
                form.setMethod(annotation.method());
                List<Input> inputs = new ArrayList<>();

                writer.write("<form action='${" + annotation.action() + "}' method='" + annotation.method() + "'>\n");
                Set<? extends Element> annElem = roundEnv.getElementsAnnotatedWith(HtmlInput.class);
                writer.write("<#list ");
                for (Element elem : annElem) {
                    HtmlInput input = elem.getAnnotation(HtmlInput.class);
                    writer.write("<input type='" + input.type() + "' name='" + input.name() + "' placeholder='" + input.placeholder() + "'>\n");
                }
                writer.write("</#list>");
                writer.write("</form>");
                writer.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

        }
        return true;
    }
}

