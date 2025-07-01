import { DownloadIcon } from "@radix-ui/react-icons";
import { Button } from "@radix-ui/themes";
import Link, { LinkProps } from "next/link";

const DownloadButton = (props: LinkProps) => (
  <Button asChild>
    <Link prefetch={false} {...props}>
      <DownloadIcon/>
    </Link>
  </Button>
);

export default DownloadButton;
