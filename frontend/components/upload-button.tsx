import { Button } from "@radix-ui/themes";
import { UploadIcon } from "@radix-ui/react-icons"
import React from "react";

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
      <UploadIcon/> Browse a file
    </Button>
  );
}
