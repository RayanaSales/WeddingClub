package converter;

import entidades.Loja;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "lojaConverter")
public class LojaConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Loja) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Loja)
        {
            component.getAttributes().put(((Loja) entity).getId().toString(), entity);           
            return ((Loja) entity).getId().toString();
        }

        return null;
    }
}
