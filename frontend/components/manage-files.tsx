import { FileIcon } from "@radix-ui/react-icons";
import Section from "./section";
import { Flex, Text } from "@radix-ui/themes";
import TextSubtle from "./text-subtle";

export default function ManageFiles() {
  const heading = "Your Files (0)";
  const subHeading = "Manage your uploaded files";
  const text = "No files uploaded yet";
  const subText = "Upload some files to get started";
  return (
    <Section icon={FileIcon} heading={heading} subHeading={subHeading}>
      <Flex justify="center" align="center" direction="column" height="100%">
        <FileIcon width="32px" height="32px" color="gray"/>
        <Text as="p" size="4">{ text }</Text>
        <TextSubtle>{ subText }</TextSubtle>
      </Flex>
    </Section>
  );
}
