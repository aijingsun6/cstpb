package cstpb.logic.diff;

import cstpb.logic.model.DescriptorHolder;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DescriptorStrictDifferTest extends AbsDifferTest {

    @Test
    public void addMessageTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-message.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("message tutorial.Hello append", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("Hello", holder.getSimpleName());
        Assert.assertEquals("tutorial.Hello", holder.getFullName());
    }

    @Test
    public void deleteMessageTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-delete-message.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.DELETE, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE, diff.getField());
        Assert.assertNull(diff.getRight());
        Assert.assertEquals("message tutorial.AddressBook delete", diff.getMsg());

        DescriptorHolder holder = diff.getLeft();
        Assert.assertEquals(left, holder.getFileName());
        Assert.assertEquals("AddressBook", holder.getSimpleName());
        Assert.assertEquals("tutorial.AddressBook", holder.getFullName());
    }

    @Test
    public void addMessageFieldTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-message-field.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("message field tutorial.Person.age append at src/test/resources/addressbook-add-message-field.proto", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("age", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.age", holder.getFullName());
    }


    @Test
    public void deleteMessageFieldTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-delete-message-field.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.DELETE, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertNull(diff.getRight());
        Assert.assertEquals("message field tutorial.Person.phones delete at src/test/resources/addressbook-delete-message-field.proto", diff.getMsg());

        DescriptorHolder holder = diff.getLeft();
        Assert.assertEquals(left, holder.getFileName());
        Assert.assertEquals("phones", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.phones", holder.getFullName());
    }

    @Test
    public void modifyMessageFieldLabelTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-message-field-label.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertEquals("modify message field tutorial.Person.phones, label LABEL_REPEATED vs LABEL_OPTIONAL at src/test/resources/addressbook-modify-message-field-label.proto", diff.getMsg());
    }

    @Test
    public void modifyMessageFieldTypeTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-message-field-type.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertEquals("modify message field tutorial.Person.phones, type tutorial.Person.PhoneNumber vs TYPE_STRING at src/test/resources/addressbook-modify-message-field-type.proto", diff.getMsg());
    }

    @Test
    public void modifyMessageFieldNumberTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-message-field-number.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertEquals("modify message field tutorial.Person.phones, number 4 vs 5 at src/test/resources/addressbook-modify-message-field-number.proto", diff.getMsg());
    }

    @Test
    public void modifyMessageFieldDefaultTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-message-field-default.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.MESSAGE_FIELD, diff.getField());
        Assert.assertEquals("modify message field tutorial.Person.PhoneNumber.type, default PHONE_TYPE_HOME vs PHONE_TYPE_UNSPECIFIED at src/test/resources/addressbook-modify-message-field-default.proto", diff.getMsg());
    }

    @Test
    public void addEnumTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-enum.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.ENUM, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("enum tutorial.Person.PersonGender append", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("PersonGender", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.PersonGender", holder.getFullName());
    }

    @Test
    public void deleteEnumTest(){
        String left = "src/test/resources/addressbook-add-enum.proto";
        String right = "src/test/resources/addressbook.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.DELETE, diff.getType());
        Assert.assertEquals(DiffField.ENUM, diff.getField());
        Assert.assertNull(diff.getRight());
        Assert.assertEquals("enum tutorial.Person.PersonGender delete", diff.getMsg());

        DescriptorHolder holder = diff.getLeft();
        Assert.assertEquals(left, holder.getFileName());
        Assert.assertEquals("PersonGender", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.PersonGender", holder.getFullName());
    }

    @Test
    public void addEnumValueTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-enum-value.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.ENUM_VALUE, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("enum value tutorial.Person.PhoneType.PHONE_TYPE_SPORT append at src/test/resources/addressbook-add-enum-value.proto", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("PHONE_TYPE_SPORT", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.PhoneType.PHONE_TYPE_SPORT", holder.getFullName());
    }

    @Test
    public void deleteEnumValueTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-delete-enum-value.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.DELETE, diff.getType());
        Assert.assertEquals(DiffField.ENUM_VALUE, diff.getField());
        Assert.assertNull(diff.getRight());
        Assert.assertEquals("enum value tutorial.Person.PhoneType.PHONE_TYPE_WORK delete at src/test/resources/addressbook-delete-enum-value.proto", diff.getMsg());

        DescriptorHolder holder = diff.getLeft();
        Assert.assertEquals(left, holder.getFileName());
        Assert.assertEquals("PHONE_TYPE_WORK", holder.getSimpleName());
        Assert.assertEquals("tutorial.Person.PhoneType.PHONE_TYPE_WORK", holder.getFullName());

    }

    @Test
    public void modifyEnumValueTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-enum-value.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.ENUM_VALUE, diff.getField());
        Assert.assertEquals("enum value tutorial.Person.PhoneType.PHONE_TYPE_WORK, 3 vs 4 at src/test/resources/addressbook-modify-enum-value.proto", diff.getMsg());
    }

    @Test
    public void addServiceTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-service.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.SERVICE, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("service tutorial.BarService append", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("BarService", holder.getSimpleName());
        Assert.assertEquals("tutorial.BarService", holder.getFullName());
    }

    @Test
    public void deleteServiceTest(){
        String left = "src/test/resources/addressbook-add-service.proto";
        String right = "src/test/resources/addressbook.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.DELETE, diff.getType());
        Assert.assertEquals(DiffField.SERVICE, diff.getField());
        Assert.assertNull(diff.getRight());
        Assert.assertEquals("service tutorial.BarService delete", diff.getMsg());

        DescriptorHolder holder = diff.getLeft();
        Assert.assertEquals(left, holder.getFileName());
        Assert.assertEquals("BarService", holder.getSimpleName());
        Assert.assertEquals("tutorial.BarService", holder.getFullName());
    }

    @Test
    public void addServiceMethodTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-add-service-method.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.ADD, diff.getType());
        Assert.assertEquals(DiffField.SERVICE_METHOD, diff.getField());
        Assert.assertNull(diff.getLeft());
        Assert.assertEquals("service method tutorial.FooService.GetSomething2 append at src/test/resources/addressbook-add-service-method.proto", diff.getMsg());

        DescriptorHolder holder = diff.getRight();
        Assert.assertEquals(right, holder.getFileName());
        Assert.assertEquals("GetSomething2", holder.getSimpleName());
        Assert.assertEquals("tutorial.FooService.GetSomething2", holder.getFullName());
    }

    @Test
    public void modifyServiceMethodTest(){
        String left = "src/test/resources/addressbook.proto";
        String right = "src/test/resources/addressbook-modify-service-method.proto";
        List<DiffItem<DescriptorHolder>> result = diffFiles(left, right);
        Assert.assertEquals(1, result.size());
        DiffItem<DescriptorHolder> diff = result.get(0);
        Assert.assertEquals(DiffType.MODIFY, diff.getType());
        Assert.assertEquals(DiffField.SERVICE_METHOD, diff.getField());
        Assert.assertEquals("service method tutorial.FooService.GetSomething modified, output tutorial.GetSomethingResponse vs tutorial.AddressBook at src/test/resources/addressbook-modify-service-method.proto", diff.getMsg());
    }


    @Override
    protected Differ getDiffer() {
        return new DescriptorStrictDiffer();
    }
}
