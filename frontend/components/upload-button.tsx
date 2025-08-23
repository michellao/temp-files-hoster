import { UploadIcon } from "@radix-ui/react-icons";
import { Button } from "@radix-ui/themes";
import type React from "react";

interface UploadButtonProps {
  onFileSelect?: () => void;
}

export default function UploadButton({ onFileSelect }: UploadButtonProps) {
  const handleClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    onFileSelect?.();
  };
  return (
    <Button type="button" onClick={handleClick}>
      <UploadIcon /> Browse a file
    </Button>
  );
}
