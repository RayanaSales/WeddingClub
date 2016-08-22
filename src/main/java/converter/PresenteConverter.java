package converter;

import entidades.Pessoa;
import entidades.Presente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import servico.PresenteServico;

@FacesConverter(value = "presenteConverter")
public class PresenteConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Presente) component.getAttributes().get(value);
        }

       return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Pessoa)
        {
            component.getAttributes().put(((Presente) entity).getId().toString(), entity);
            return ((Presente) entity).getId().toString();
        }

        return null;
    }
}
