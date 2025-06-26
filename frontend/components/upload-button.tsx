import { Button } from "@radix-ui/themes";
import { UploadIcon } from "@radix-ui/react-icons"

export default function UploadButton() {
    return (
        <Button>
            <UploadIcon/> Browse a file
        </Button>
    );
}
