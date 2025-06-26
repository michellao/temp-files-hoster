import { UploadIcon } from "@radix-ui/react-icons";
import Section from "./section";
import UploadButton from "./upload-button";
import { Flex, Text } from "@radix-ui/themes";
import TextSubtle from "./text-subtle";
import styles from "./section-style.module.css";

export default function UploadFiles() {
  const heading = "Upload a file";
  const subHeading = "Drag and drop files here or click to browse";
  const text = "";
  const subText = "";
  return (
    <Section icon={UploadIcon} heading={heading} subHeading={subHeading}>
      <Flex justify="center" align="center" direction="column" height="100%" className={styles.border}>
        <UploadIcon width="32px" height="32px" color="gray"/>
        <Text as="p" size="4">Choose files or drag them here</Text>
        <TextSubtle>Support for any file type up to 500 MB</TextSubtle>
        <UploadButton/>
      </Flex>
    </Section>
  );
}
