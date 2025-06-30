'use client';
import { UploadIcon } from "@radix-ui/react-icons";
import Section from "./section";
import UploadButton from "./upload-button";
import { Flex, Text } from "@radix-ui/themes";
import TextSubtle from "./text-subtle";
import styles from "./section-style.module.css";
import React from "react";
import { addUploaded } from "@/storage/localstorage";

export default function UploadFiles({
  onFileUploaded
}: {
  onFileUploaded: () => void
}) {
  const heading = "Upload a file";
  const subHeading = "Drag and drop files here or click to browse";
  const text = "Choose files or drag them here";
  const subText = "Support for any file type up to 500 MB";

  const backendUrl = process.env.NEXT_PUBLIC_BACKEND_URL!;

  const triggerFileInput = () => {
    document.getElementById('file-upload')?.click();
  };

  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const form = document.querySelector('form');
    const selectedFile = e.target.files?.[0];
    if (selectedFile && form) {
      const fileName = selectedFile.name;
      const formData = new FormData(form);
      console.log('Form data:', formData);
      try {
        const result = await upload(formData);
        if (result.ok) {
          const url = result.body;
          const token = result.headers.get("X-Token");
          console.log(`Uploaded ${url} and Token ${token}`);
          if (url && token) {
            const reader = url.getReader();
            let data = await reader.read();
            const urlString = new TextDecoder().decode(data.value);
            addUploaded(fileName, token, urlString);
            onFileUploaded();
          }

        }
      } catch (e: any) {
        console.log('Failed to uploaded:', e);
      }
    }
  };

  function upload(formData: FormData) {
    const result = fetch(backendUrl, {
      method: 'POST',
      body: formData,
    });
    return result;
  }

  return (
    <Section icon={UploadIcon} heading={heading} subHeading={subHeading} height="250px">
      <form action="" method="POST" encType="multipart/form-data">
          <Flex justify="center" align="center" direction="column" height="100%" className={styles.border} asChild>
            <label htmlFor="file-upload" style={{ cursor: 'pointer' }}>
            <UploadIcon width="32px" height="32px" color="gray"/>
            <Text as="p" size="4">{ text }</Text>
            <TextSubtle>{ subText }</TextSubtle>
            <UploadButton onFileSelect={triggerFileInput} />
            <input type="file" id="file-upload" name="file" style={{ display: 'none' }} onChange={handleFileChange} />
          </label>
          </Flex>
      </form>
    </Section>
  );
}
