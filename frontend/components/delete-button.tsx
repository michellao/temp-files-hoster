import { TrashIcon } from "@radix-ui/react-icons";
import { Button, ButtonProps } from "@radix-ui/themes";

const DeleteButton = (props: ButtonProps) => (
  <Button color="red" variant="outline" {...props}><TrashIcon/></Button>
);

export default DeleteButton;
