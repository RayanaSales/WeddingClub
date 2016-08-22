
package converter;

import entidades.Noivo;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "noivoConverter")
public class NoivoConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Noivo) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Noivo)
        {
            component.getAttributes().put(((Noivo) entity).getId().toString(), entity);           
            return ((Noivo) entity).getId().toString();
        }

        return null;
    }
}
