package converter;

import entidades.Buffet;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "buffetConverter")
public class BuffetConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Buffet) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Buffet)
        {
            component.getAttributes().put(((Buffet) entity).getId().toString(), entity);
            return ((Buffet) entity).getId().toString();
        }

        return null;
    }
}
