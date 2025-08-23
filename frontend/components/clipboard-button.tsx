import { ClipboardIcon } from "@radix-ui/react-icons";
import { Button, type ButtonProps } from "@radix-ui/themes";

function setClipboard(url: string) {
  navigator.clipboard.writeText(url);
}

const ClipboardButton = ({
  props,
  url,
}: {
  props?: ButtonProps;
  url: string;
}) => (
  <Button {...props} onClick={() => setClipboard(url)}>
    <ClipboardIcon />
  </Button>
);

export default ClipboardButton;
