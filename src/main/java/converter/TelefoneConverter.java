package converter;

import entidades.Telefone;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "telefoneConverter")
public class TelefoneConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value != null && !value.isEmpty())
        {
            return (Telefone) component.getAttributes().get(value);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity)
    {
        if (entity != null && entity instanceof Telefone)
        {
            component.getAttributes().put(((Telefone) entity).getId().toString(), entity);           
            return ((Telefone) entity).getId().toString();
        }

        return null;
    }
}
