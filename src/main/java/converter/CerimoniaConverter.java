package converter;

import entidades.Cerimonia;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "cerimoniaConverter")
public class CerimoniaConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Cerimonia) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Cerimonia)
        {
            component.getAttributes().put(((Cerimonia) entity).getId().toString(), entity);
            return ((Cerimonia) entity).getId().toString();
        }

        return null;
    }
}
