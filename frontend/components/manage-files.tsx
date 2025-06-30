import { FileIcon } from "@radix-ui/react-icons";
import Section from "./section";
import { Flex, Text } from "@radix-ui/themes";
import TextSubtle from "./text-subtle";
import { getAllUploaded } from "@/storage/tools";
import DisplayFile from "./display-file";

export default function ManageFiles({ counter }: { counter: number }) {
  const heading = `Your Files (${counter})`;
  const subHeading = "Manage your uploaded files";
  const text = "No files uploaded yet";
  const subText = "Upload some files to get started";

  let subContent: React.ReactNode = (
    <>
      <FileIcon width="32px" height="32px" color="gray"/>
      <Text as="p" size="4">{ text }</Text>
      <TextSubtle>{ subText }</TextSubtle>
    </>
  );
  if (counter > 0) {
    const uploadedData = getAllUploaded();
    subContent = uploadedData.map((d) => <DisplayFile key={d.url} fileName={d.fileName} url={d.url} />);
  }

  return (
    <Section icon={FileIcon} heading={heading} subHeading={subHeading} height={counter <= 0 ? '250px' : undefined}>
      <Flex justify="center" align={counter <= 0 ? 'center' : undefined} direction="column" height="100%">
        { subContent }
      </Flex>
    </Section>
  );
}
